type e 
	k as string
 	i as integer
end type

type o 
	 x as e
end type

type w
 	f as boolean
	t as o
end type

type q
	l as integer
	v as boolean
	t as w
end type

function f(byval b as integer) as integer
	dim i as q
	i.t.t.x.i = i.t.t.x.i + 10 * i.t.t.x.i
		if i.t.t.x.i > 0 and i.t.t.x.i < 11 or not (i.t.t.x.i = 5) then
		end if
	return i.t.t.x.i * 5
end function

sub main()
	console.write(f(10))
end sub
