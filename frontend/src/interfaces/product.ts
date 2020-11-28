import { Category } from '@/interfaces/category';
import { Tag } from '@/interfaces/tag';

export interface ProductAverageRatings {
  boxRating: number;
  boxReusabilityRating: number;
  productReusabilityRating: number;
}

export interface Product {
  id?: number;
  name: string;
  category: Category;
  tags: Tag[];
  averageRatings: ProductAverageRatings;
}
