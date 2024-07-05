
INSERT INTO account (id, owner, account_number, balance, created_at)
SELECT * FROM (
                  SELECT 1 as id, 'John Doe' as owner, '22334455' as account_number, 500.00 as balance, now() as created_at
              ) AS tmp
WHERE NOT EXISTS (
    SELECT id FROM account WHERE id = 1
);

INSERT INTO account (id, owner, account_number, balance, created_at)
SELECT * FROM (
                  SELECT 2 as id, 'Mike Smith' as owner, '11223344' as account_number, 1000.00 as balance, now() as created_at
              ) AS tmp
WHERE NOT EXISTS (
    SELECT id FROM account WHERE id = 2
);

SELECT setval('account_id_seq', (SELECT MAX(id) FROM account));