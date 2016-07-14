package beans;

import java.util.ArrayList;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private String address;
    private ArrayList<Product> basket;

    public User() {
        this.basket = new ArrayList<Product>();
    }
    
    public double totalBasket(){
        double total = 0;
        for(int i =0; i < this.basket.size(); i++){
            total += basket.get(i).getCost();
        }
        
        return total;
    }
    
    public void addToBasket(Product product) {
        this.basket.add(product);
    }

    public void deleteItemInBacket(int place) {
        this.basket.remove(place);
    }

    public void removeBasket() {
        this.basket.clear();
    }

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the isAdmin
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the basket
     */
    public ArrayList<Product> getBasket() {
        return basket;
    }

    /**
     * @param basket the basket to set
     */
    public void setBasket(ArrayList<Product> basket) {
        this.basket = basket;
    }

}
