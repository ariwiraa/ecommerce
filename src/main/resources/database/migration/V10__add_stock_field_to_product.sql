-- Menambahkan field stock baru ke tabel product
ALTER TABLE product ADD COLUMN stock_quantity INTEGER NOT NULL DEFAULT 0;

-- Menambahkan index untuk field stock_quantity
CREATE INDEX idx_product_stock_quantity ON product(stock_quantity);
