import { Category } from "@/interfaces/category";
import { Tag } from "@/interfaces/tag";

export interface ProductAverageRatings {
  boxFromRecycling: number;
  boxRecycable: number;
  boxReusable: number;
  productFromRecycling: number;
  productRecycable: number;
  productReusable: number;
  repairable: number;
}

export interface Product {
  id?: number;
  name: string;
  category: Category;
  tags: Tag[];
  averageRatings: ProductAverageRatings;
  photoUrl: string;
}

export const defaultProduct = {
  name: "",
  barCode: "",
  categoryId: 0,
  category: { name: "" },
  tags: [],
  photoUrl: "",
  averageRatings: {
    boxFromRecycling: 0,
    boxRecycable: 0,
    boxReusable: 0,
    productFromRecycling: 0,
    productRecycable: 0,
    productReusable: 0,
    repairable: 0
  }
};
