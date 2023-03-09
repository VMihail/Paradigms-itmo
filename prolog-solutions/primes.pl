init(MAX_N) :- sieve_of_Eratosthenes(2, MAX_N).

inc(N, R) :- number(N), !, R is N + 1.
inc(N, R) :- number(R), !, N is R - 1.

prime(N) :- \+ compositeTable(N), !.

make_composite(N, J, MAX_N) :-
    N =< MAX_N,
    assert(compositeTable(N)), 
    NN is N + J, 
    make_composite(NN, J, MAX_N).

sieve_of_Eratosthenes(N, MAX_N) :-
	K is N * N,
	K =< MAX_N,
	\+compositeTable(N),
	NN is 2 * N,
	make_composite(NN, N, MAX_N).

sieve_of_Eratosthenes(N, MAX_N) :-
	N =< MAX_N,
	inc(N,R),
	sieve_of_Eratosthenes(R, MAX_N).

composite(N) :- compositeTable(N), !.

next_prime(N, A) :- 
    inc(N, R), 
    prime(R), !, 
    A is R.

next_prime(N, A) :-
    inc(N, R), 
    \+prime(R), 
    next_prime(R, M), 
    A is M.

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).

divisors(1, _, []).
divisors(N, D, [N]) :- prime(N).
divisors(N, D, R1) :- 
    N >= D * D, 
    0 is mod(N, D), 
    M is div(N, D), 
    prime(D), 
    divisors(M, D, R2), !, 
    concat([D | _], R2, R1).

divisors(N, D, R1) :- 
    N >= D * D, 
    \+(0 is mod(N, D)), 
    next_prime(D, DN), 
    divisors(N, DN, R1).

multiply_list([], R) :- R is 1.
multiply_list([H | T], R) :- 
    multiply_list(T, R1),
     R is H * R1.

correct_sort_list([H]) :- prime(H).
correct_sort_list([H1, H2 | T]) :- 
    H1 =< H2, 
    prime(H1), 
    correct_sort_list([H2 | T]).

prime_divisors(N, R) :- 
    integer(N), divisors(N, 2, R), !.

prime_divisors(N, [H | T]) :- 
    correct_sort_list([H | T]), 
    multiply_list([H | T], N1), 
    N is N1, !.

prime_divisors(N, []) :- N is 1, !.




correct_sort_unique_list([H]) :- prime(H).
correct_sort_unique_list([H1, H2 | T]) :- 
    H1 < H2, 
    prime(H1), 
    correct_sort_list([H2 | T]).

unique([H], [H]) :- !.
unique([H1, H2| T], R) :- 
    H1 \= H2, 
    unique([H2 | T], R1), 
    concat([H1], R1, R), !.

unique([H1, H2| T], R) :- 
    H1 is H2, 
    unique([H2 | T], R1), 
    concat([], R1, R), !.

unique_prime_divisors(1, []) :- !.
unique_prime_divisors(N, R) :- 
    integer(N), 
    divisors(N, 2, R1), 
    unique(R1, R), !.

unique_prime_divisors(N, [H | T]) :- 
    correct_sort_unique_list([H | T]), 
    multiply_list([H | T], N1), 
    N is N1, !.
