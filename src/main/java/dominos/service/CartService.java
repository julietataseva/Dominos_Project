package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import dominos.model.dto.*;
import dominos.model.pojo.*;
import dominos.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private AdditionalProductRepository additionalProductRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DoughRepository doughRepository;

    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    public AdditionalProductDTO addAdditionalProductToCart(int productId, Map<IProductDTO, Integer> cart) {
        Optional<AdditionalProduct> additionalProduct = getAdditionalProduct(productId);
        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct.get());

        if (!cart.containsKey(additionalProductDTO)) {
            cart.put(additionalProductDTO, 1);
        } else {
            cart.put(additionalProductDTO, cart.get(additionalProductDTO) + 1);
        }

        return additionalProductDTO;
    }

    public AdditionalProductDTO decreaseAdditionalProductQuantityInCart(int productId, Map<IProductDTO, Integer> cart) {
        this.checkIfCartIsEmpty(cart);
        Optional<AdditionalProduct> additionalProduct = getAdditionalProduct(productId);
        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct.get());

        if (!cart.containsKey(additionalProductDTO)) {
            throw new NotFoundException("No such product in cart");
        } else {
            cart.put(additionalProductDTO, cart.get(additionalProductDTO) - 1);
        }

        if (cart.get(additionalProductDTO) <= 0) {
            cart.remove(additionalProductDTO);
        }

        return additionalProductDTO;
    }

    public AdditionalProductDTO deleteAdditionalProductFromCart(int productId, Map<IProductDTO, Integer> cart) {
        this.checkIfCartIsEmpty(cart);
        Optional<AdditionalProduct> additionalProduct = getAdditionalProduct(productId);
        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct.get());

        if (!cart.containsKey(additionalProductDTO)) {
            throw new NotFoundException("No such product in cart");
        }

        cart.remove(additionalProductDTO);
        return additionalProductDTO;
    }

    public List<CartResponseDTO> getCart(Map<IProductDTO, Integer> cartAttribute) {
        List<CartResponseDTO> cart = new ArrayList<>();
        for (Map.Entry<IProductDTO, Integer> entry : cartAttribute.entrySet()) {
            CartResponseDTO cartItem = new CartResponseDTO(entry.getKey(), entry.getValue());
            cart.add(cartItem);
        }

        return cart;
    }

    public PizzaOrderDTO addPizzaToCart(RequestPizzaOrderDTO requestPizzaOrderDTO, Map<IProductDTO, Integer> cart) {
        PizzaOrderDTO pizzaOrderDTO = this.getPizzaOrderDTO(requestPizzaOrderDTO);
        if (!cart.containsKey(pizzaOrderDTO)) {
            cart.put(pizzaOrderDTO, 1);
        } else {
            cart.put(pizzaOrderDTO, cart.get(pizzaOrderDTO) + 1);
        }

        return pizzaOrderDTO;
    }

    public PizzaOrderDTO decreasePizzaQuantityInCart(RequestPizzaOrderDTO requestPizzaOrderDTO, Map<IProductDTO, Integer> cart) {
        this.checkIfCartIsEmpty(cart);
        PizzaOrderDTO pizzaOrderDTO = this.getPizzaOrderDTO(requestPizzaOrderDTO);
        if (!cart.containsKey(pizzaOrderDTO)) {
            throw new NotFoundException("No such pizza in cart");
        } else {
            if(cart.get(pizzaOrderDTO) > 1) {
                cart.put(pizzaOrderDTO, cart.get(pizzaOrderDTO) - 1);
            }
            else{
                cart.remove(pizzaOrderDTO);
            }
        }

        return pizzaOrderDTO;
    }

    public PizzaOrderDTO deletePizzaFromCart(RequestPizzaOrderDTO requestPizzaOrderDTO, Map<IProductDTO, Integer> cart) {
        this.checkIfCartIsEmpty(cart);
        PizzaOrderDTO pizzaOrderDTO = this.getPizzaOrderDTO(requestPizzaOrderDTO);

        if (!cart.containsKey(pizzaOrderDTO)) {
            throw new NotFoundException("No such product in cart");
        }

        cart.remove(pizzaOrderDTO);
        return pizzaOrderDTO;
    }

    private void checkIfCartIsEmpty(Map<IProductDTO, Integer> cart) {
        if (cart.isEmpty()) {
            throw new BadRequestException("Cart is empty!");
        }
    }

    private Optional<AdditionalProduct> getAdditionalProduct(int productId) {
        Optional<AdditionalProduct> additionalProduct = additionalProductRepository.findById(productId);
        if (additionalProduct.isEmpty()) {
            throw new NotFoundException("No such product");
        } else {
            return additionalProduct;
        }
    }

    private PizzaOrderDTO getPizzaOrderDTO(RequestPizzaOrderDTO requestPizzaOrderDTO){
        int pizzaId = requestPizzaOrderDTO.getPizzaId();
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(pizzaId);

        if (pizzaOptional.isEmpty()) {
            throw new BadRequestException("This pizza doesn't exist!");
        }

        PizzaAddedToCartDTO pizza = new PizzaAddedToCartDTO(pizzaOptional.get());

        Integer doughTypeId = requestPizzaOrderDTO.getDoughTypeId();
        Optional<Dough> doughOptional = null;
        Dough dough = null;
        if (doughTypeId != null) {
            doughOptional = doughRepository.findById(doughTypeId);
            if (doughOptional.isEmpty()) {
                throw new BadRequestException("This dough doesn't exist!");
            }
            dough = doughOptional.get();
        } else {
            dough = new Dough();
        }

        DoughDTO doughDTO = new DoughDTO(dough);

        Integer pizzaSizeId = requestPizzaOrderDTO.getPizzaSizeId();
        Optional<PizzaSize> pizzaSizeOptional = null;
        PizzaSize pizzaSize = null;
        if (pizzaSizeId != null) {
            pizzaSizeOptional = pizzaSizeRepository.findById(pizzaSizeId);
            if (pizzaSizeOptional.isEmpty()) {
                throw new BadRequestException("This pizza size doesn't exists!");
            }
            pizzaSize = pizzaSizeOptional.get();
        } else {
            pizzaSize = new PizzaSize();
        }

        PizzaSizeDTO pizzaSizeDTO = new PizzaSizeDTO(pizzaSize);

        List<Ingredient> additionalIngredients = new ArrayList<>();
        List<Integer> additionalIngredientsIds = requestPizzaOrderDTO.getAdditionalIngredientsIds();
        if (additionalIngredientsIds != null) {
            for (Integer ingredientId : additionalIngredientsIds) {
                Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
                if (ingredientOptional.isEmpty()) {
                    throw new BadRequestException("This ingredient doesn't exist!");
                }
                Ingredient ingredient = ingredientOptional.get();
                additionalIngredients.add(ingredient);
            }
        }

        List<IngredientWithPriceDTO> additionalIngredientsWithPrice = new ArrayList<>();
        for (Ingredient ingredient : additionalIngredients) {
            additionalIngredientsWithPrice.add(new IngredientWithPriceDTO(ingredient));
        }

        PizzaOrderDTO pizzaOrderDTO = new PizzaOrderDTO();
        pizzaOrderDTO.setId(pizza.getId());
        pizzaOrderDTO.setPizza(pizza);
        pizzaOrderDTO.setDough(doughDTO);
        pizzaOrderDTO.setPizzaSize(pizzaSizeDTO);
        pizzaOrderDTO.setAdditionalIngredients(additionalIngredientsWithPrice);
        return pizzaOrderDTO;
    }
}