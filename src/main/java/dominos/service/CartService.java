package dominos.service;

import dominos.exceptions.BadRequestException;
import dominos.model.dto.*;
import dominos.model.dto.additional_product_dto.AdditionalProductDTO;
import dominos.model.dto.ingredient_dto.IngredientWithPriceDTO;
import dominos.model.dto.pizza_dto.PizzaAddedToCartDTO;
import dominos.model.dto.pizza_dto.PizzaOrderDTO;
import dominos.model.dto.pizza_dto.PizzaSizeDTO;
import dominos.model.dto.pizza_dto.RequestPizzaOrderDTO;
import dominos.model.pojo.*;
import dominos.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        AdditionalProduct additionalProduct = getAdditionalProduct(productId);
        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct);

        if (!cart.containsKey(additionalProductDTO)) {
            cart.put(additionalProductDTO, 1);
        } else {
            cart.put(additionalProductDTO, cart.get(additionalProductDTO) + 1);
        }

        return additionalProductDTO;
    }

    public AdditionalProductDTO decreaseAdditionalProductQuantityInCart(int productId, Map<IProductDTO, Integer> cart) {
        this.checkIfCartIsEmpty(cart);
        AdditionalProduct additionalProduct = getAdditionalProduct(productId);
        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct);

        if (!cart.containsKey(additionalProductDTO)) {
            throw new BadRequestException("No such product in cart");
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
        AdditionalProduct additionalProduct = getAdditionalProduct(productId);
        AdditionalProductDTO additionalProductDTO = new AdditionalProductDTO(additionalProduct);

        if (!cart.containsKey(additionalProductDTO)) {
            throw new BadRequestException("No such product in cart");
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
            throw new BadRequestException("No such pizza in cart");
        } else {
            if (cart.get(pizzaOrderDTO) > 1) {
                cart.put(pizzaOrderDTO, cart.get(pizzaOrderDTO) - 1);
            } else {
                cart.remove(pizzaOrderDTO);
            }
        }

        return pizzaOrderDTO;
    }

    public PizzaOrderDTO deletePizzaFromCart(RequestPizzaOrderDTO requestPizzaOrderDTO, Map<IProductDTO, Integer> cart) {
        this.checkIfCartIsEmpty(cart);
        PizzaOrderDTO pizzaOrderDTO = this.getPizzaOrderDTO(requestPizzaOrderDTO);

        if (!cart.containsKey(pizzaOrderDTO)) {
            throw new BadRequestException("No such product in cart");
        }

        cart.remove(pizzaOrderDTO);
        return pizzaOrderDTO;
    }

    private void checkIfCartIsEmpty(Map<IProductDTO, Integer> cart) {
        if (cart.isEmpty()) {
            throw new BadRequestException("Cart is empty!");
        }
    }

    private AdditionalProduct getAdditionalProduct(int productId) {
        Optional<AdditionalProduct> additionalProduct = additionalProductRepository.findById(productId);
        if (additionalProduct.isEmpty()) {
            throw new BadRequestException("No such product");
        } else {
            return additionalProduct.get();
        }
    }

    private PizzaOrderDTO getPizzaOrderDTO(RequestPizzaOrderDTO requestPizzaOrderDTO) {
        PizzaAddedToCartDTO pizzaAddedToCartDTO = this.getPizzaAddedToCartDTO(requestPizzaOrderDTO);
        DoughDTO doughDTO = this.getDoughDTO(requestPizzaOrderDTO);
        PizzaSizeDTO pizzaSizeDTO = this.getPizzaSizeDTO(requestPizzaOrderDTO);
        List<IngredientWithPriceDTO> additionalIngredientsWithPrice = this.getIngredientWithPriceDTOs(requestPizzaOrderDTO);
        int pizzaId = pizzaAddedToCartDTO.getId();

        PizzaOrderDTO pizzaOrderDTO = new PizzaOrderDTO(pizzaId, pizzaAddedToCartDTO, doughDTO,
                pizzaSizeDTO, additionalIngredientsWithPrice);

        return pizzaOrderDTO;
    }

    private PizzaAddedToCartDTO getPizzaAddedToCartDTO(RequestPizzaOrderDTO requestPizzaOrderDTO) {
        int pizzaId = requestPizzaOrderDTO.getPizzaId();
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(pizzaId);

        if (pizzaOptional.isEmpty()) {
            throw new BadRequestException("This pizza doesn't exist!");
        }

        PizzaAddedToCartDTO pizza = new PizzaAddedToCartDTO(pizzaOptional.get());
        return pizza;
    }

    private DoughDTO getDoughDTO(RequestPizzaOrderDTO requestPizzaOrderDTO) {
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
        return doughDTO;
    }

    private PizzaSizeDTO getPizzaSizeDTO(RequestPizzaOrderDTO requestPizzaOrderDTO) {
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
        return pizzaSizeDTO;
    }

    private List<IngredientWithPriceDTO> getIngredientWithPriceDTOs(RequestPizzaOrderDTO requestPizzaOrderDTO) {
        List<Ingredient> additionalIngredients = new ArrayList<>();
        List<Integer> additionalIngredientsIds = requestPizzaOrderDTO.getAdditionalIngredientsIds();
        List<IngredientWithPriceDTO> additionalIngredientsWithPrice = new ArrayList<>();
        if (additionalIngredientsIds != null) {
            for (Integer ingredientId : additionalIngredientsIds) {
                Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
                if (ingredientOptional.isEmpty()) {
                    throw new BadRequestException("This ingredient doesn't exist!");
                }
                Ingredient ingredient = ingredientOptional.get();
                additionalIngredients.add(ingredient);
            }

            for (Ingredient ingredient : additionalIngredients) {
                additionalIngredientsWithPrice.add(new IngredientWithPriceDTO(ingredient));
            }
        }

        return additionalIngredientsWithPrice;
    }
}