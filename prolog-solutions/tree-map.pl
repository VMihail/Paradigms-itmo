% тесты не проходит
% Exception in thread "main" java.lang.AssertionError: Exactly one solution expected for V in map_get(node(9,qixo,64284,empty,empty),9,V)
%    found: 0 []
%map_minKey(Map, Key)
%map_maxKey(Map, Key)
%map_build(ListMap, TreeMap)
%map_get(TreeMap, Key, Value)

%map_put(TreeMap, Key, Value, Result)
%map_remove(TreeMap, Key, Result)
%map_build(ListMap, TreeMap) nlogn

%Hard

inc(N, R) :- number(N), !, R is N + 1.
inc(R, N) :- number(N), !, R is N - 1.

split(empty, V, empty, empty) :- !.
split(node(K, V, P, L, R), X, node(K, V, P, L, R1), Result2) :-
    K < X,
    split(R, X, R1, Result2),
    !.
split(node(K, V, P, L, R), X, Result1, node(K, V, P, L1, R)) :-
	split(L, X, Result1, L1).

merge(F, empty, F) :- !.
merge(empty, S, S) :- !.
merge(node(K1, V1, P1, L1, R1), node(K2, V2, P2, L2, R2), node(K1, V1, P1, L1, Result)) :-
	P1 > P2,
	merge(R1, node(K2, V2, P2, L2, R2), Result),
	!.
merge(node(K1, V1, P1, L1, R1), node(K2, V2, P2, L2, R2), node(K2, V2, P2, Result, R2)) :-
    merge(node(K1, V1, P1, L1, R1), L2, Result).

map_get(node(K, V, Neutral, Neutral, Neutral), K, V) :- !.
map_get(node(K, Neutral, Neutral, L, R), Key, V) :-
    Key < K,
    map_get(L, Key, V),
    !.
map_get(node(K, Neutral, Neutral, L, R), Key, V) :-
    map_get(R, Key, V),
    !.

map_remove(Tree, K, Result) :-
	split(Tree, K, T1, T2),
	split(T2, K + 1, Neutral, T3),
	merge(T1, T3, Result).

map_put(Tree, K, V, Result) :-
	split(Tree, K, T1, T2),
	split(T2, K + 1, Neutral, T3),
	rand_int(100000, Rnd),
	merge(T1, node(K, V, Rnd, empty, empty), T4),
	merge(T4, T3, Result), !.

contains(V, [V | T]).
contains(V, [H | T]) :- contains(V, T).

map_build([], empty) :- !.
map_build([(K, V) | T], TreeMap) :-
    write((K, V)), nl, map_build(T, TreeMapS),
    map_put(TreeMapS, K, V, TreeMap).

map_minKey(node(K, Neutral, Neutral, empty, R), K) :- !.
map_minKey(node(K, Neutral, Neutral, L, R), Key) :-
    map_minKey(L, Key).

map_maxKey(node(K, Neutral, Neutral, L, empty), K) :- !.
map_maxKey(node(K, Neutral, Neutral, L, R), Key) :-
    map_maxKey(R, Key).
