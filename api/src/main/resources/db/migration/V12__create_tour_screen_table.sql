CREATE TABLE tour_screen (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    screen VARCHAR(50) NOT NULL CHECK (screen IN ('LOT', 'LOTDETAILS', 'N')),
    completed BOOLEAN NOT NULL DEFAULT false
);