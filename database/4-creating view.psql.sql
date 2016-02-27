-- dayly_balance
CREATE VIEW dayly_balance AS
SELECT d.id AS id_day, SUM( clk.clockout - clk.clockin ) AS total_hours_day, ( SUM( clk.clockout - clk.clockin ) - d.expected_hours ) AS interval_day, d.date date_day
FROM day d JOIN clockinclockout clk ON d.id = clk.id_day
WHERE clk.clockin IS NOT NULL AND clk.clockout IS NOT NULL
GROUP BY d.id
ORDER BY d.date;
