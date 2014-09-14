TokenizerParser
===============

Tokenize, parse, intepret for the following grammar: 

    assign = id ’=’ expr
    expr = term [{(’+’ | ’-’) term}]
    term = factor [{(’*’ | ’/’) factor}]
    factor = int | ’(’ expr ’)’
    
    Where int is defined as (0..9)+ and id as (a..z)+
