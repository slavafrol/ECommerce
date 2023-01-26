import java.util.ArrayList;

public class Cart 
{
    private ArrayList<CartItem> items = new ArrayList<CartItem>();

    public Cart(ArrayList<CartItem> items) //If we want to create a cart using an existing list of cart items
    {
        this.items = items;
    }

    public Cart() //Cart has no variables, so the constructor is empty too
    {

    }

    public ArrayList<CartItem> getItems()
    {
        return items;
    }

    public String addItem(CartItem item)
    {
        items.add(item);
        return "#" + items.size();
    }

    public String remItem(String productId)
    {
        String num = null;
        for (CartItem it : items){
            if (it.getProduct().getId().equals(productId)){
                num = "#" + (items.indexOf(it) + 1);
                items.remove(it);
                break;
            }
        }
        return num;
    }

    public void emptyCart(){
        items.clear();
    }
}
