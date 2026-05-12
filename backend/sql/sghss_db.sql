
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    username VARCHAR(120) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS administrator (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf CHAR(11) UNIQUE NOT NULL,
    card_id VARCHAR(80) NOT NULL,
    job_title VARCHAR(50) DEFAULT 'administrator',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS patient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    cpf CHAR(11) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    birth_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS doctor (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    job_title VARCHAR(80),
    specialty VARCHAR(100),
    crm VARCHAR(20) NOT NULL,
    cpf CHAR(11) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS appointment (
    id SERIAL PRIMARY KEY,
    patient_id INTEGER NOT NULL,
    doctor_id INTEGER NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    type VARCHAR(50),
    status VARCHAR(20) DEFAULT 'Scheduled',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    username VARCHAR(150) NOT NULL,
    role VARCHAR(20) NOT NULL,
    room_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

INSERT INTO users (
    name,
    username,
    email,
    password,
    role
) VALUES (
    'admin',
    'admin',
    'admin@email.com',
    'admin',
    'ADMIN'
);

INSERT INTO administrator (
    name,
    email,
    cpf,
    card_id,
    job_title
) VALUES (
             'admin',
             'admin@email.com',
             '09876543210',
             '54687921355',
             'administrator'
         );