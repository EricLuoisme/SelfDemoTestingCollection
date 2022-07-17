package effective_java.builder;

import java.util.Objects;

public class NyPizza extends Pizza {

    public enum Size {SMALL, MEDIUM, BIG}

    private final Size size;

    public NyPizza(Builder builder) {
        // get toppings first
        super(builder);
        // get stuff only belongs to this concrete class, only from its own builder
        this.size = builder.size;
    }

    public static class Builder extends Pizza.Builder<Builder> {

        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public static void main(String[] args) {
        // now can build a NyPizza from it's builder
        NyPizza nyPizza = new Builder(Size.MEDIUM)
                .addingTopping(Topping.ONION)
                .addingTopping(Topping.MUSHROOM)
                .build();
        // it contains Pizza's features, as well as NyPizza's ones
        System.out.println(nyPizza.toppings);
        System.out.println(nyPizza.size);
    }
}
