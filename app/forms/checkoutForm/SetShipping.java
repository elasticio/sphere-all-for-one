package forms.checkoutForm;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.client.shop.model.Address;
import io.sphere.client.shop.model.Customer;
import play.data.validation.Constraints;

public class SetShipping {

    @Constraints.Required(message = "Shipping method required")
    public String method;

    @Constraints.Required(message = "First name required")
    public String firstName;

    @Constraints.Required(message = "Last name required")
    public String lastName;

    @Constraints.Email(message = "Invalid value for email")
    public String email;

    @Constraints.Required(message = "Street address required")
    public String street;

    public String street2;

    @Constraints.Required(message = "Postal code required")
    public String postalCode;

    @Constraints.Required(message = "City required")
    public String city;

    @Constraints.Required(message = "Country required")
    @Constraints.Pattern(value = "DE|CN|RU", message = "Invalid value for country")
    public String country;

    public SetShipping() {

    }

    public SetShipping(Address address) {
        if (address != null) {
            this.firstName = address.getFirstName();
            this.lastName = address.getLastName();
            this.email = address.getEmail();
            this.street = address.getStreetName();
            this.street2 = address.getStreetNumber();
            this.postalCode = address.getPostalCode();
            this.city = address.getCity();
            this.country = address.getCountry().getAlpha2();
        }
    }

    public SetShipping(Customer customer) {
        if (customer != null) {
            this.firstName = customer.getName().getFirstName();
            this.lastName = customer.getName().getLastName();
            this.email = customer.getEmail();
        }
    }

    public Address getAddress() {
        Address address = new Address(getCountryCode());
        address.setFirstName(firstName);
        address.setLastName(lastName);
        address.setEmail(email);
        address.setStreetName(street);
        address.setStreetNumber(street2);
        address.setPostalCode(postalCode);
        address.setCity(city);
        return address;
    }

    public CountryCode getCountryCode() {
        return CountryCode.getByCode(this.country);
    }

}