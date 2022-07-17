package effective_java.builder;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class Pizza {

    // different toppings
    public enum Topping {
        HAM, MUSHROOM, ONION, PEPPER, SAUSAGE
    }

    // toppings can be added multiple times, thus, using a set for storing
    final Set<Topping> toppings;

    // abstract builder inside class PIZZA, let the classes that extends it, have to
    // construct the builder too
    abstract static class Builder<T extends Builder<T>> {

        // each class that extends this builder, need to have an topping
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        // child-classes can directly call the addingTopping method, then get an return of it's own implement
        public T addingTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        // make sure how to return a new object
        abstract Pizza build();

        // for return itself, get a builder
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        // get a concrete topping set
        toppings = builder.toppings.clone();
    }


}
