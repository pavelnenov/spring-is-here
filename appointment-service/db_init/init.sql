CREATE DATABASE IF NOT EXISTS appointments_db;

use appointments_db;

-- hospitals Table
CREATE TABLE IF NOT EXISTS hospitals
(
    hospital_id INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    location    VARCHAR(255) NOT NULL
);

-- doctors Table
CREATE TABLE IF NOT EXISTS doctors
(
    doctor_id      INT PRIMARY KEY AUTO_INCREMENT,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    specialization VARCHAR(255),
    hospital_id    INT,
    email          VARCHAR(255) UNIQUE,
    phone          VARCHAR(50),
    FOREIGN KEY (hospital_id) REFERENCES hospitals (hospital_id)
);

-- patients Table
CREATE TABLE IF NOT EXISTS patients
(
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    dob        DATE,
    gender     ENUM ('Male', 'Female', 'Other'),
    email      VARCHAR(255),
    phone      VARCHAR(50)
);

-- appointments Table
CREATE TABLE IF NOT EXISTS appointments
(
    appointment_id   INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id        INT,
    patient_id       INT,
    hospital_id      INT,
    appointment_date DATETIME NOT NULL,
    status           ENUM ('Scheduled', 'Completed', 'Cancelled'),
    notes            TEXT,
    FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id),
    FOREIGN KEY (patient_id) REFERENCES patients (patient_id),
    FOREIGN KEY (hospital_id) REFERENCES hospitals (hospital_id)
);

-- medical_records Table
CREATE TABLE IF NOT EXISTS medical_records
(
    record_id   INT PRIMARY KEY AUTO_INCREMENT,
    patient_id  INT,
    doctor_id   INT,
    date        DATETIME NOT NULL,
    description TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id)
);

-- prescriptions Table
CREATE TABLE IF NOT EXISTS prescriptions
(
    prescription_id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id       INT,
    patient_id      INT,
    medication      VARCHAR(255) NOT NULL,
    dosage          VARCHAR(50),
    frequency       VARCHAR(50),
    duration        VARCHAR(50),
    FOREIGN KEY (doctor_id) REFERENCES doctors (doctor_id),
    FOREIGN KEY (patient_id) REFERENCES patients (patient_id)
);


-- Insert into hospitals
INSERT IGNORE INTO hospitals (hospital_id, name, location)
VALUES (1, 'General Hospital', 'New York'),
       (2, 'Specialty Hospital', 'Chicago');

-- Insert into doctors
INSERT IGNORE INTO doctors (doctor_id, first_name, last_name, specialization, hospital_id, email, phone)
VALUES (1, 'John', 'Doe', 'Cardiology', 1, 'john.doe@email.com', '1234567890'),
       (2, 'Jane', 'Doe', 'Neurology', 1, 'jane.doe@email.com', '0987654321'),
       (3, 'Emily', 'Smith', 'Orthopedics', 2, 'emily.smith@email.com', '1112223333');

-- Insert into patients
INSERT IGNORE INTO patients (patient_id, first_name, last_name, dob, gender, email, phone)
VALUES (1, 'Alice', 'Johnson', '1990-01-01', 'Female', 'alice.johnson@email.com', '5556667777'),
       (2, 'Bob', 'Williams', '1985-05-15', 'Male', 'bob.williams@email.com', '7778889999');

-- Insert into appointments
INSERT IGNORE INTO appointments (appointment_id, doctor_id, patient_id, hospital_id, appointment_date, status, notes)
VALUES (1, 1, 1, 1, '2023-07-01 10:00:00', 'Scheduled', 'Initial checkup'),
       (2, 2, 2, 1, '2023-07-02 11:00:00', 'Scheduled', 'Follow-up'),
       (3, 3, 1, 2, '2023-07-03 09:30:00', 'Scheduled', 'Ortho consultation');

-- Insert into medical_records
INSERT IGNORE INTO medical_records (record_id, patient_id, doctor_id, date, description)
VALUES (1, 1, 1, '2023-01-01 10:00:00', 'General check-up. No issues found.'),
       (2, 2, 2, '2023-02-01 10:00:00', 'Neuro consult. Recommended tests.');

-- Insert into prescriptions
INSERT IGNORE INTO prescriptions (prescription_id, doctor_id, patient_id, medication, dosage, frequency, duration)
VALUES (1, 1, 1, 'MedicineA', '20mg', 'Once a day', '1 week'),
       (2, 2, 2, 'MedicineB', '10mg', 'Twice a day', '2 weeks');
