import java.lang.reflect.Array;

public class Shoes extends Product
{
    private String brand;
    private String shoeType;
    int[] blackStockCount;
    int[] brownStockCount;
    //I decided to have two lists with 5 variables each for individual stock counts
    //Values with indices 0, 1, 2, 3, 4 represent sizes 6, 7, 8, 9, 10 respectively

    public Shoes(String name, String id, double price, int[] blackStockCount, int[] brownStockCount, String brand, String shoeType)
    {
        //As the inherited stockCount variable is not used throughout the program, I simply set it to 1000
        super(name, id, price, 1000, Product.Category.SHOES);
        this.brand = brand;
        this.shoeType = shoeType;
        this.blackStockCount = blackStockCount;
        this.brownStockCount = brownStockCount;
    }
    public boolean validOptions(String productOptions)
    {
        //Separating color and size from the productOptions string and putting them in separate values for easier access
        //I wanted to have the size and color as two separate arguments, but the task was specifying to have them concatenated
        if (!(productOptions.equals(""))){
            String color;
            int size = Integer.parseInt(productOptions.substring(0, 1));
            if (size == 1){ //If the size is 10, the first character would be 1
                color = productOptions.substring(2);
                size = 10;
            }
            else{
                color = productOptions.substring(1);
            }
            if (color.equals("Black") || color.equals("Brown")){
                if (size == 6 || size == 7 || size == 8 || size == 9 || size == 10)
                    return true;
            }
        }
        return false;
    }

    public int getStockCount(String productOptions)
    {
        String color;
        int size = Integer.parseInt(productOptions.substring(0, 1));
        if (size == 1){
            color = productOptions.substring(2);
            size = 10;
        }
        else{
            color = productOptions.substring(1);
        }
        if (color.equals("Black")){
            return blackStockCount[size-6]; //Index size-6 is used because the values in the list are for the sizes 6, 7, 8, 9, 10 respectively
        }
        else if (color.equals("Brown")){
            return brownStockCount[size-6];
        }
        return 0;
    }

    public void setStockCount(int stockCount, String productOptions)
    {
        String color;
        int size = Integer.parseInt(productOptions.substring(0, 1));
        if (size == 1){
            color = productOptions.substring(2);
            size = 10;
        }
        else{
            color = productOptions.substring(1);
        }
        if (color.equals("Black")){
            blackStockCount[size-6] = stockCount;
        }
        else if (color.equals("Brown")){
            brownStockCount[size-6] = stockCount;
        }
    }

    public void reduceStockCount(String productOptions)
    {
        String color;
        int size = Integer.parseInt(productOptions.substring(0, 1));
        if (size == 1){
            color = productOptions.substring(2);
            size = 10;
        }
        else{
            color = productOptions.substring(1);
        }
        if (color.equals("Black")){
            blackStockCount[size-6]--;
        }
        else if (color.equals("Brown")){
            brownStockCount[size-6]--;
        }
    }
    
    public void print(){
        super.print();
        System.out.println(" Brand: " + brand + " Type: " + shoeType);
    }
}
