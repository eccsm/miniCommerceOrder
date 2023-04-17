package tr.nttdata.poc.minicommerce.order.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import tr.nttdata.poc.minicommerce.order.model.Cart;

@Repository
public class CartRepository {
    private static final String HASH_NAME = "shopping_cart";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Cart findCartByUsername(String username) {
        HashOperations<String, String, Cart> hashOperations = redisTemplate.opsForHash();

        return hashOperations.get(HASH_NAME, username);
    }

    public void save(String username, Cart cart) {
        HashOperations<String, String, Cart> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(HASH_NAME, username, cart);
    }
}
