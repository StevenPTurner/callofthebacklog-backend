INSERT INTO media_type (id, name)
VALUES
    (1, 'Movie'),
    (2, 'Game'),
    (3, 'Book'),
    (4, 'Anime');

INSERT INTO backlog (id, media_type_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);


INSERT INTO media (backlog_id, title) VALUES
    -- Movies
    (1, 'Inception'),
    (1, 'The Godfather'),
    (1, 'Parasite'),
    (1, 'Interstellar'),
    (1, 'The Shawshank Redemption'),

    -- Games
    (2, 'The Legend of Zelda: Breath of the Wild'),
    (2, 'Red Dead Redemption 2'),
    (2, 'God of War'),
    (2, 'Elden Ring'),
    (2, 'Hollow Knight'),

    -- Books
    (3, 'To Kill a Mockingbird'),
    (3, '1984'),
    (3, 'The Catcher in the Rye'),
    (3, 'Pride and Prejudice'),
    (3, 'The Hobbit'),

    -- Anime
    (4, 'Attack on Titan'),
    (4, 'Spirited Away'),
    (4, 'Death Note'),
    (4, 'Fullmetal Alchemist: Brotherhood'),
    (4, 'Jujutsu Kaisen');
