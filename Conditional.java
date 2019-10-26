import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by kaushik.i on 06/06/19.
 */
public class Conditional<T> {
    private final T object;
    private final Supplier<Boolean> condition;
    private List<Function<T,T>> trueConsumers;
    private List<Function<T,T>> falseConsumers;
    public Conditional(T t, Supplier<Boolean> condition) {
        this.object = t;
        this.condition = condition;
        this.trueConsumers = new ArrayList<>();
        this.falseConsumers = new ArrayList<>();
    }

    public static <T> Conditional<T> of(T object, Supplier<Boolean> condition) {
        return new Conditional<>(object, condition);
    }

    public Conditional<T> ifTrue(Function<T,T> trueConsumer) {
        this.trueConsumers.add(trueConsumer);
        return this;
    }

    public Conditional<T> orElse(Function<T,T> falseConsumer) {
        this.falseConsumers.add(falseConsumer);
        return this;
    }

    public T get() {
        if(condition.get()) {
            trueConsumers.stream().forEach(trueConsumer -> trueConsumer.apply(this.object));
        }else {
            falseConsumers.stream().forEach(falseConsumer -> falseConsumer.apply(this.object));
        }
        return this.object;
    }
}
