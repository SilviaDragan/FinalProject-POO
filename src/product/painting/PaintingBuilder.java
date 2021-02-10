package product.painting;

import product.ProductBuilder;

/**
 * This class builds a product that also has properties of a painting
 */
public class PaintingBuilder extends ProductBuilder<Painting, PaintingBuilder> {

   public PaintingBuilder withPainter(String painterName) {
       specificProduct.setPainterName(painterName);
       return this;
   }

   public PaintingBuilder withColor(String color) {
       specificProduct.setColorType(color);
       return this;
   }

    @Override
    protected Painting getSpecificProduct() {
        return new Painting();
    }

    @Override
    protected PaintingBuilder getSpecificBuilder() {
        return this;
    }
}
