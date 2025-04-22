export const getCategoryIcon = (category: string) => {
    switch (category) {
      case 'FOOD':
        return 'food';
      case 'ENTERTAINMENT':
        return 'movie';
      case 'LIVING':
        return 'home';
      case 'MEDICAL':
        return 'medical-bag';
      case 'CLOTHING':
        return 'tshirt-crew';
      case 'PERSONAL':
        return 'face-man-profile';
      case 'FITNESS':
        return 'dumbbell';
      case 'TRANSPORT':
        return 'car';
      default:
        return 'help-circle'; // fallback icon
    }
  };