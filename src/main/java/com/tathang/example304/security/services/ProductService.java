package com.tathang.example304.security.services;

import org.springframework.stereotype.Service;

import com.tathang.example304.model.Category;
import com.tathang.example304.model.Product;
import com.tathang.example304.repository.CategoryRepository;
import com.tathang.example304.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository,
            CategoryRepository categoryRepository,
            FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileStorageService = fileStorageService;
    }

    // === CREATE PRODUCT ===
    public Product createProduct(String name, String description, BigDecimal price,
            Long categoryId, Integer stockQuantity, String imageUrl) {

        System.out.println("=== 🏭 PRODUCT SERVICE: createProduct ===");
        System.out.println("📦 Creating product with data:");
        System.out.println("  Name: " + name);
        System.out.println("  Description: " + description);
        System.out.println("  Price: " + price);
        System.out.println("  Category ID: " + categoryId);
        System.out.println("  Stock Quantity: " + stockQuantity);
        System.out.println("  Image URL: " + imageUrl);

        try {
            // Kiểm tra category
            System.out.println("🔍 Looking for category with ID: " + categoryId);
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> {
                        System.out.println("❌ Category not found: " + categoryId);
                        return new RuntimeException("Category not found with id: " + categoryId);
                    });

            System.out.println("✅ Category found: " + category.getName());

            // Tạo product
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            product.setStockQuantity(stockQuantity);
            product.setImageUrl(imageUrl);
            product.setArchive(false);

            System.out.println("💾 Saving product to database...");
            Product saved = productRepository.save(product);

            System.out.println("🎉 Product saved successfully!");
            System.out.println("   Product ID: " + saved.getId());
            System.out.println("=========================================");

            return saved;

        } catch (Exception e) {
            System.out.println("❌ PRODUCT SERVICE ERROR: " + e.getMessage());
            e.printStackTrace();
            System.out.println("=========================================");
            throw e;
        }
    }

    // === UPDATE PRODUCT ===
    public Product updateProduct(Long id, String name, String description, BigDecimal price,
            Long categoryId, Integer stockQuantity, String imageUrl) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setStockQuantity(stockQuantity);
        if (imageUrl != null) {
            product.setImageUrl(imageUrl);
        }

        return productRepository.save(product);
    }

    // === DELETE PRODUCT (XÓA KHỎI DB + XÓA FILE ẢNH) ===
    public boolean deleteProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            // Xóa sản phẩm khỏi database
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // === GET ALL PRODUCTS ===
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByArchiveFalse();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // === CATEGORY METHODS ===
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
