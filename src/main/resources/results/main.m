val = jsondecode(fileread('results.json'));
vertices = arrayfun(@(x) str2double(x.params.vertices), val);
time = arrayfun(@(x) x.primaryMetric.score, val);
err = arrayfun(@(x) x.primaryMetric.scoreError, val);

x = linspace(vertices(1), vertices(end), 100);

f = @(n) (n .^ 4 .* log(n));

corr = corrcoef(f(vertices), time);

fig0 = figure;
subplot(2, 1, 1);
plot(x, f(x));
xlabel('Количество вершин');
ylabel('Миллисекунды');
title('Точная оценка');

subplot(2, 1, 2);
errorbar(vertices, time, err);
xlabel('Количество вершин');
ylabel('Миллисекунды');
title('Результат эксперимента');

fig1 = figure;
plot(f(vertices), time, 'o');
xlabel('Теоретическая оценка в миллисекундах');
ylabel('Практический результат в миллисекундах');
title('Диаграмма рассеяния');

saveas(fig0, 'fig0.png');
saveas(fig1, 'fig1.png')