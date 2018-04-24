package ntamtech.adinz.interfaces;

/**
 * Created by bassiouny on 24/04/18.
 */

public interface BaseResponseInterface<T> {
    void onSuccess(T t);
    void onFailed(String errorMessage);
}
