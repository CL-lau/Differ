package com.differ.dispatcher;

import com.differ.interfacer.Dispatcher;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
@Data
@NoArgsConstructor
public class GlobalDispatcherMap {
    private Map<String, Dispatcher> dispatcherMap = new ConcurrentHashMap<>();

    // (baseUrl + baseUrl) (ip:port + ip:port)
    public boolean addDispatcher(String key, Dispatcher dispatcher){
        if (dispatcherMap.containsKey(key)){
            return false;
        }else {
            dispatcherMap.put(key, dispatcher);
            return true;
        }
    }

    public boolean containsDispatcher(String key){
        return this.dispatcherMap.containsKey(key);
    }

    public boolean removeDispatcher(String key, Dispatcher dispatcher){
        if (this.dispatcherMap.containsKey(key)){
            return this.dispatcherMap.remove(key, dispatcher);
        }else {
            return true;
        }
    }

    public boolean removeDispatcher(String key){
        if (this.dispatcherMap.containsKey(key)){
            return !Objects.isNull(this.dispatcherMap.remove(key));
        }else {
            return true;
        }
    }

    public Dispatcher removeDispatcherWithReturn(String key){
        if (this.dispatcherMap.containsKey(key)){
            return this.dispatcherMap.remove(key);
        }else {
            return null;
        }
    }

    public int getDispatcherCount() {
        return this.dispatcherMap.size();
    }
}
