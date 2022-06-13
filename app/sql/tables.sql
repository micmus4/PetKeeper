CREATE TABLE animals(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    date_of_birth DATE NOT NULL,
    photo_name TEXT,
    species_id INTEGER NOT NULL REFERENCES species( id )
);

CREATE TABLE species(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    info TEXT
);

CREATE TABLE alert(
    id SERIAL PRIMARY KEY,
    animal_id INTEGER NOT NULL REFERENCES animals( id ),
    title TEXT NOT NULL,
    description TEXT,
    due_date DATE NOT NULL
);