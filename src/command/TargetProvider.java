package command;

@FunctionalInterface
public interface TargetProvider<T> {
    T getTarget();
}
