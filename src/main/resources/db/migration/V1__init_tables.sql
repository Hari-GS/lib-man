CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(50),
    published_date DATE,
    available_copies INT
);

CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(20),
    registered_date DATE
);

CREATE TABLE borrow (
    id SERIAL PRIMARY KEY,
    member_id INT REFERENCES member(id),
    book_id INT REFERENCES book(id),
    borrowed_date DATE,
    due_date DATE
);
