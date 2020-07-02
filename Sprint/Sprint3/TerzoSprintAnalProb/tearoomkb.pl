%===========================================
% tearoomkb.pl
%===========================================

%% ------------------------------------------ 
%% Positions
%% ------------------------------------------ 
pos( barman,       6, 0 ).
pos( home,		   0, 0 ).
pos( teatable1,    2, 2 ).
pos( teatable2,    4, 2 ).
pos( entrancedoor, 0, 4 ).
pos( exitdoor,     6, 4 ).


%% ------------------------------------------ 
%% Teatables
%% ------------------------------------------ 
%% busy
%% free		(not busy and not clean)
%% dirty	(not clean)
%% clean	(not dirty)
%% available (free and clean)	

%%teatable con due parametri, il secondo identifica lo stato.
%%invece di mettere un terzo parametro per il cliente, utilizziamo il secondo per inglobare
%%l'informazione del clientID.
%%Ovvero, teatable(1,occupy(ID))

teatable( 1, clean ). 
teatable( 2, clean ).

setState(N,S) :-
	retract(teatable(N,_)),
	!,
	assert(teatable(N,S)).

getState(N,S) :-
	teatable(N,S).

tableclean(N) :- teatable( N, clean), !.

numfreetables(N) :-
	findall( N,teatable( N,clean ), NList),
	%% stdout <- println( tearoomkb_numfreetables(NList) ),
	length(NList,N).

stateOfTeatables( [teatable1(V1),teatable2(V2)] ) :-
	teatable( 1, V1 ),
	teatable( 2, V2 ).

occupyTable(N,ID) :-
	retract( teatable( N, clean ) ),
	!,
	assert( teatable( N, occupy(ID) ) ).
occupyTable(_,_).

releaseTable(N) :-
	retract( teatable( N, occupy ) ),
	!,
	assert( teatable( N, dirty ) ).
	
	
cleanTable(N)	 :-
	%% stdout <- println( tearoomkb_cleanTable(N) ),
	retract( teatable( N, sanitized ) ),
	!,
	assert( teatable( N, clean ) ).
cleanTable(N).	

%% ------------------------------------------ 
%% Waiter
%% ------------------------------------------ 

%%  athome
%%	serving( CLIENTID )
%%	movingto( CELL )
%%	cleaning( table(N) )

waiter( athome ).	

%% ------------------------------------------ 
%% ServiceDesk
%% ------------------------------------------ 
%% idle
%% preparing( CLIENTID )
%% ready( CLIENTID )

servicedesk( idle ).

%% ------------------------------------------ 
%% Room as a whole
%% ------------------------------------------ 
roomstate(  waiter(S), stateOfTeatables(V), servicedesk(D) ):-
	 waiter(S), stateOfTeatables(V), servicedesk(D).
	 
	