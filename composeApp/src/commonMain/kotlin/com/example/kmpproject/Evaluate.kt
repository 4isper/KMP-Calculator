import kotlin.math.pow

// Поддерживаемые операторы и их приоритеты
val operatorPrecedence = mapOf(
    '+' to 1,
    '-' to 1,
    '*' to 2,
    '/' to 2,
    '^' to 3
)

// Основная функция для вычисления выражения
fun evaluate(expression: String): Double {
    val tokens = tokenize(expression)
    val values = mutableListOf<Double>()
    val operators = mutableListOf<Char>()

    tokens.forEach { token ->
        when {
            token.isNumber() -> {
                values.add(token.toDouble())
            }
            token.first().isOperator() -> {  // Проверяем первый символ токена
                while (operators.isNotEmpty() && hasPrecedence(token.first(), operators.last())) {
                    values.add(applyOperation(operators.removeLast(), values.removeLast(), values.removeLast()))
                }
                operators.add(token.first())
            }
            token == "(" -> {
                operators.add('(')
            }
            token == ")" -> {
                while (operators.last() != '(') {
                    values.add(applyOperation(operators.removeLast(), values.removeLast(), values.removeLast()))
                }
                operators.removeLast()
            }
        }
    }

    // Выполнить оставшиеся операции
    while (operators.isNotEmpty()) {
        values.add(applyOperation(operators.removeLast(), values.removeLast(), values.removeLast()))
    }

    return values.last()
}

// Токенизация выражения
fun tokenize(expression: String): List<String> {
    val tokens = mutableListOf<String>()
    var number = StringBuilder()

    expression.forEachIndexed { index, ch ->
        when {
            ch.isDigit() || ch == '.' -> {
                number.append(ch)  // Собираем число
            }
            ch.isOperator() -> {
                if (number.isNotEmpty()) {
                    tokens.add(number.toString())  // Добавляем накопленное число
                    number = StringBuilder()
                }

                // Проверяем на отрицательное число
                if (ch == '-' && (tokens.isEmpty() || tokens.last() == "(" || tokens.last().first().isOperator())) {
                    number.append(ch)  // Собираем отрицательное число
                } else {
                    tokens.add(ch.toString())  // Добавляем оператор
                }
            }
            ch == '(' || ch == ')' -> {
                if (number.isNotEmpty()) {
                    tokens.add(number.toString())  // Добавляем накопленное число
                    number = StringBuilder()
                }
                tokens.add(ch.toString())  // Добавляем скобку
            }
        }
    }

    if (number.isNotEmpty()) {
        tokens.add(number.toString())  // Добавляем последнее накопленное число
    }

    return tokens
}

// Проверка приоритета операторов
fun hasPrecedence(operator1: Char, operator2: Char): Boolean {
    return operator2 != '(' && operator2 != ')' && operatorPrecedence[operator1]!! <= operatorPrecedence[operator2]!!
}

// Применение операции к двум значениям
fun applyOperation(op: Char, b: Double, a: Double): Double {
    return when (op) {
        '+' -> a + b
        '-' -> a - b
        '*' -> a * b
        '/' -> a / b
        '^' -> a.pow(b)
        else -> throw IllegalArgumentException("Неверный оператор: $op")
    }
}

// Расширение для проверки, является ли строка числом
fun String.isNumber(): Boolean {
    return this.toDoubleOrNull() != null
}

// Расширение для проверки, является ли символ оператором
fun Char.isOperator(): Boolean {
    return this == '+' || this == '-' || this == '*' || this == '/' || this == '^'
}

