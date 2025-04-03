package com.maverickdevs.expensebuddy.services.impl;

import com.maverickdevs.expensebuddy.dto.request.PaymentRequestDTO;
import com.maverickdevs.expensebuddy.entities.Payment;
import com.maverickdevs.expensebuddy.entities.Split;
import com.maverickdevs.expensebuddy.entities.User;
import com.maverickdevs.expensebuddy.repositories.PaymentRepository;
import com.maverickdevs.expensebuddy.repositories.SplitRepository;
import com.maverickdevs.expensebuddy.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl {

    private final PaymentRepository paymentRepository;
    private final SplitRepository splitRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, SplitRepository splitRepository, UserRepository userRepository){
        this.paymentRepository = paymentRepository;
        this.splitRepository = splitRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Payment addPayment(PaymentRequestDTO paymentRequestDTO){
        //paymentRepository.save(payment);
        User creditor = userRepository.findById(paymentRequestDTO.getCreditorId()).orElseThrow(() -> new RuntimeException("User not found"));
        User debtor = userRepository.findById(paymentRequestDTO.getDebtorId()).orElseThrow(() -> new RuntimeException("User not found"));

        List<Split> splits = splitRepository.findAllById(paymentRequestDTO.getSplitIdList());

        if(splits.isEmpty()) {
            throw new IllegalArgumentException("No split Ids sent by the user");
        }

        splits.forEach(split -> {
            if (!split.getDebtor().equals(debtor) || !split.getCreditor().equals(creditor)) {
                throw new IllegalArgumentException("Split doesn't match payment users");
            }
            if(split.getIsSettled()){
                throw new IllegalArgumentException("Split Id is already settled");
            }
        });

        // Calculate total amount (using Stream API to avoid lambda issues)
        BigDecimal totalAmt = splits.stream()
                .map(Split::getSplitAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Validate payment amount matches splits (or allow partial payments?)
        if (totalAmt.compareTo(paymentRequestDTO.getAmount()) != 0) {
            throw new IllegalArgumentException(
                    "Payment amount (" + paymentRequestDTO.getAmount() + ") " +
                            "does not match total splits amount (" + totalAmt + ")"
            );
        }

        // Mark splits as settled
        splits.forEach(split -> {
            split.setIsSettled(true);
            split.setSettledAt(LocalDateTime.now());
        });


        Payment payment = Payment.builder()
                .amount(paymentRequestDTO.getAmount())
                .paidAt(LocalDateTime.now())
                .debtor(debtor)
                .creditor(creditor)
                .build();

        Payment createdPayment = paymentRepository.save(payment);
        splitRepository.saveAll(splits);


        return createdPayment;
    }
}
