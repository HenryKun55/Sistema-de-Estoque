CREATE TABLE IF NOT EXISTS clients (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 name TEXT NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS category (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 name TEXT NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 name TEXT NOT NULL,
 password TEXT NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP
);
 
CREATE TABLE IF NOT EXISTS products (
 id INTEGER PRIMARY KEY,
 category_id INTEGER,
 product_name TEXT NOT NULL,
 storage INTEGER,
 photo_url TEXT,
 storage_date_in TIMESTAMP WITH TIME ZONE,
 storage_date_out TIMESTAMP WITH TIME ZONE, 
 created_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS inputs (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 id_entrada INTEGER NOT NULL,
 product_id INTEGER NOT NULL,
 storage_in INTEGER NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS outputs (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 id_saida INTEGER NOT NULL,
 product_id INTEGER NOT NULL,
 client_id INTEGER NOT NULL,
 storage_out INTEGER NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (product_id) REFERENCES product(id),
 FOREIGN KEY (client_id) REFERENCES client(id)
);