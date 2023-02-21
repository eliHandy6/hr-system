CREATE TABLE hrm.staff_category
(
    code            BIGSERIAL PRIMARY KEY,
    category        character varying NOT NULL,
    payroll_enabled boolean DEFAULT false,
    input_date      date,
    user_name       character varying,
    approval_level  integer DEFAULT 0,
    payment_type    character varying,
    company_id      integer DEFAULT 1
);