-- Create the database
CREATE DATABASE IF NOT EXISTS student_marks;
-- Connect to the database
\ c student_marks;
-- Create student table
CREATE TABLE IF NOT EXISTS student (
    name VARCHAR(100) NOT NULL,
    roll_num VARCHAR(20) PRIMARY KEY
);
-- Create subject table
CREATE TABLE IF NOT EXISTS subject (
    subject_id SERIAL PRIMARY KEY,
    subject_name VARCHAR(50) NOT NULL UNIQUE
);
-- Create marks table
CREATE TABLE IF NOT EXISTS marks (
    roll_num VARCHAR(20) NOT NULL,
    subject_id INTEGER NOT NULL,
    exam_type VARCHAR(20) NOT NULL CHECK (exam_type IN ('monthly', 'mid', 'annual')),
    marks INTEGER NOT NULL CHECK (
        marks >= 0
        AND marks <= 100
    ),
    PRIMARY KEY (roll_num, subject_id, exam_type),
    FOREIGN KEY (roll_num) REFERENCES student(roll_num),
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);
-- Insert default subjects
INSERT INTO subject (subject_name)
VALUES ('Maths') ON CONFLICT (subject_name) DO NOTHING;
INSERT INTO subject (subject_name)
VALUES ('Science') ON CONFLICT (subject_name) DO NOTHING;
INSERT INTO subject (subject_name)
VALUES ('Marathi') ON CONFLICT (subject_name) DO NOTHING;
INSERT INTO subject (subject_name)
VALUES ('Social') ON CONFLICT (subject_name) DO NOTHING;
INSERT INTO subject (subject_name)
VALUES ('Kannada') ON CONFLICT (subject_name) DO NOTHING;
INSERT INTO subject (subject_name)
VALUES ('Hindi') ON CONFLICT (subject_name) DO NOTHING;
INSERT INTO subject (subject_name)
VALUES ('English') ON CONFLICT (subject_name) DO NOTHING;