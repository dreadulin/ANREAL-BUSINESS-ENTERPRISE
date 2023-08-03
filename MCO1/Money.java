public class Money {
    protected int value;
    protected int amount;

    public Money(int value, int amount) {
        this.value = value;
        this.amount = amount;
    }

    public boolean subtractAmount(int amount) {
        if (this.amount - amount >= 0) {
            this.amount -= amount;
            return true;
        }
        return false;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public int collectValue() {
        int collected = this.value * this.amount;
        this.amount = 0;
        return collected;
    }

    public int getValue() {
        return this.value;
    }

    public int getAmount() {
        return this.amount;
    }

    public void display() {
        System.out.println("P" + this.value + " - " + this.amount + "x");
    }
}
