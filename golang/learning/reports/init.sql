create table reports (
    id int not null PRIMARY KEY AUTO_INCREMENT,
    body text
);
insert into reports
  (body)
VALUES
  ('hoge'), ('piyo'), ('fuga');