export interface Category {
    id: number;
    name: string;
    icon: string;
  }
  
  export interface ExpenseBase {
    amount: number;
    category: Category;
    description: string;
    date: string
  }
  
  export interface PersonalExpense extends ExpenseBase {
    type: 'personal';
  }
  
  export interface GroupExpense extends ExpenseBase {
    type: 'group';
    splitType: 'equal' | 'custom';
    splitAmounts: Record<string, number>;
    participants: string[];
  }
  
  export type Expense = PersonalExpense | GroupExpense;
  
  export interface GroupMember {
    id: string;
    name: string;
  }