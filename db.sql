CREATE TABLE cliente (
    id_cliente BINARY(16) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cliente_desde DATE
);

CREATE TABLE produto (
    id_produto BINARY(16) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    em_estoque BOOLEAN NOT NULL
);

CREATE TABLE pedido (
    id_pedido BINARY(16) PRIMARY KEY,
    cliente_id BINARY(16) NOT NULL,

    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id_cliente)
);

CREATE TABLE item_pedido (
    id_item BINARY(16) PRIMARY KEY,
    pedido_id BINARY(16) NOT NULL,
    produto_id BINARY(16) NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id_pedido),

    CONSTRAINT fk_item_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id_produto)
);