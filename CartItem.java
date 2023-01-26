public class CartItem {
    Product product;
    String productOptions;

    public CartItem(Product product, String productOptions)
    {
        this.product = product;
        this.productOptions = productOptions;
    }

    public Product getProduct()
    {
        return product;
    }
    
    public String getOption()
    {
        return productOptions;
    }
}
