package controllers;

import com.google.common.base.Optional;
import controllers.actions.SaveContext;
import models.*;
import play.libs.F;
import play.mvc.Content;
import play.mvc.Result;
import play.mvc.With;
import services.*;
import views.html.productCategoryView;
import views.html.productSearchView;

@With(SaveContext.class)
public class ProductListController extends BaseController {

    public ProductListController(CategoryService categoryService, ProductService productService,
                                 CartService cartService, CustomerService customerService) {
        super(categoryService, productService, cartService, customerService);
    }

    public F.Promise<Result> categoryProducts(String categorySlug, int page, int show, String sort, String list) {
        final Optional<ShopCategory> category = categoryService().getBySlug(locale(), categorySlug);
        final UserContext context = userContext(cart(), customer());
        if (category.isPresent()) {
            RequestParameters parameters = RequestParameters.of(queryString(), page, show, sort, list);
            return productService().fetchCategoryProducts(locale(), category.get(), parameters)
                    .map(new F.Function<ProductList, Result>() {
                        @Override
                        public Result apply(ProductList productList) throws Throwable {
                            if (productList.isEmpty()) {
                                flash("info-product-list", "No products found");
                            }
                            CommonDataBuilder data = data(context).withCategory(category.get());
                            return ok(showProductCategoryPage(data, productList, category.get()));
                        }
                    });
        } else {
            return F.Promise.<Result>pure(notFound(showNotFoundPage(data(context))));
        }
    }

    public F.Promise<Result> searchProducts(int page, int show, String sort, String list) {
        final UserContext context = userContext(cart(), customer());
        final RequestParameters parameters = RequestParameters.of(queryString(), page, show, sort, list);
        return productService().fetchSearchedProducts(locale(), parameters)
                .map(new F.Function<ProductList, Result>() {
                    @Override
                    public Result apply(ProductList productList) throws Throwable {
                        if (productList.isEmpty()) {
                            flash("info-product-list", "No products found");
                        }
                        return ok(showProductSearchPage(data(context), productList));
                    }
                });

    }

    static Content showProductCategoryPage(CommonDataBuilder dataBuilder, ProductList productList, ShopCategory category) {
        CommonData data = dataBuilder.withCategory(category).build();
        return productCategoryView.render(data, productList, category);
    }

    static Content showProductSearchPage(CommonDataBuilder dataBuilder, ProductList productList) {
        return productSearchView.render(dataBuilder.build(), productList);
    }
}