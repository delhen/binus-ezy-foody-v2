package com.example.binusezyfoody2;

public class ProductDetail {
    private String name;
    private int imageResourceId;
    private String description;
    private int price;
    private int product_type_id;

    // Menu Category
    // 1: Drink
    // 2: Food
    // 3: Snack
    public static ProductDetail[] productsDetail = {
            new ProductDetail("EzyFoody Popcorn", R.drawable.snack1,
                    "Popcorn regular untuk mengganjal perutmu yang sedang lapar. Harga terjangkau" +
                            "dengan rasa layaknya makanan dengan harga yg tidak terjangkau.", 40000, 3),
            new ProductDetail("Chocolate Cookies", R.drawable.snack2,
                    "Cookies dengan rasa yang luar biasa. Dapat kamu gunakan untuk mengganjal perutmu" +
                            "apabila sedang lapar ketika perjalanan pulang.", 20000, 3),
            new ProductDetail("Wafer Sehat", R.drawable.snack3,
                    "Wafer sehat yang mengandung komposisi kacang dan buah. Untuk kamu yang ingin makan snack" +
                            "dengan kadar kesehatan yang baik, kamu cocok untuk mencicipi snack ini.", 50000, 3),
            new ProductDetail("Salad Sayur Ayam", R.drawable.food1,
                    "Untuk kamu yang suka sayur tapi gamau penuh dengan itu, kami sediakan salad sayur" +
                            "dengan sedikit perpaduan ayam agar anda tidak bosan saat memakannya.", 38000, 2),
            new ProductDetail("Roti Telur Daun", R.drawable.food2,
                    "Untuk kamu yang semi vegetarian, kamu bisa coba menu ini. Menu ini kaya akan gizi" +
                            "yang dapat membuatmu semangat dalam menjalankan kehidupan sehari-hari.", 25000, 2),
            new ProductDetail("Roti Blueberry", R.drawable.food3,
                    "Roti yang dihindangkna dengan banyak blueberry serta dilumuri oleh madu akan membuat" +
                            "anda semakin semangat untuk menjalani sulitnya dunia ini. Memang mahal, tetapi rasanya" +
                            "terjamin enak.", 80000, 2),
            new ProductDetail("Strawberry Cocktail", R.drawable.drink1,
                    "Ini adalah minuman stroberi terbaik yang terdapat di dalam produk kami! " +
                            "Dengan cita rasa asam dan segar, minuman ini jadi favorit di sini.", 300000, 1),
            new ProductDetail("Milkshake Mango Smoothies", R.drawable.drink2,
                    "Ini adalah milkshake dengan susu yang diimpor dari dalam negeri. " +
                            "Minuman ini juga salah satu yang terbaik di sini.", 60000, 1),
            new ProductDetail("Coffe Latte", R.drawable.drink3,
                    "Kopi adalah favorit kita semua. Begitu juga di EzyFood dimana kami memiliki kopi yang berkualitas" +
                            "dengan bartender yang berkualitas pula.", 90000, 1)
    };

    public ProductDetail(String name, int imageResourceId, String description, int price, int product_type_id) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.description = description;
        this.price = price;
        this.product_type_id = product_type_id;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getProduct_type_id() {
        return product_type_id;
    }
}
