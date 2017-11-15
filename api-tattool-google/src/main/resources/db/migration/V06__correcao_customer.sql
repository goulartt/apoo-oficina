use oficina;
ALTER TABLE oficina.CUSTOMER 
DROP COLUMN birthDate;

ALTER TABLE oficina.CUSTOMER
ADD COLUMN birth_date DATE;

ALTER TABLE oficina.ADDRESS 
DROP COLUMN zipCode;

ALTER TABLE oficina.ADDRESS
ADD COLUMN zip_code int;