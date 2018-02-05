CREATE TABLE ticks (
    id serial PRIMARY KEY,
    coin_market_cap_id TEXT NOT NULL,
    name TEXT NOT NULL,
    symbol TEXT NOT NULL,
    rank integer NOT NULL,
    price_usd numeric(20,8) NOT NULL,
    price_btc numeric(20,8) NOT NULL,
    volume_usd_24h numeric(20,2) NOT NULL,
    market_cap_usd numeric(20,2),
    available_supply numeric(20,2),
    total_supply numeric(20,2),
    max_supply numeric(20,2),
    percent_change_1h numeric(5,2) NOT NULL,
    percent_change_24h numeric(5,2) NOT NULL,
    percent_change_7d numeric(5,2) NOT NULL,
    last_updated timestamp with time zone NOT NULL
);

