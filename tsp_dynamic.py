def tsp():
    C = [
        [-1, 10, 15, 20],
        [10, -1, 35, 25],
        [15, 35, -1, 30],
        [20, 25, 30, -1]
    ]
#    C = [
#        [-1, 2, 9, 10],
#        [1, -1, 6, 4],
#        [15, 7, -1, 8],
#        [6, 3, 12, -1]
#    ]
    n = len(C)
    S = [
        [2], [3], [4], [2, 3],
        [2, 4], [3, 4]
    ]
    S_final = [2, 3, 4]
    G = {}
    P = {}

    for k in range(1, n + 1):
        P[k] = {}
        G[k] = {}
        G[k][tuple([])] = C[k-1][0]
        P[k][tuple([])] = C[k-1][0]

    min_dist_s = None
    for s in S:
        for k in range(2, n + 1):
            if k not in s:
                min_dist = 10000
                for _s in s:
                    c = C[k - 1][_s - 1]
                    s_without__s = s[:]
                    s_without__s.remove(_s)
                    g = G[_s][tuple(s_without__s)]
                    if (c + g) < min_dist:
                        min_dist = c + g
                        min_dist_s = _s
                        G[k][tuple(s)] = c + g

                P[k][tuple(s)] = min_dist_s

    min_dist = 10000
    k = 1
    for _s in S_final:
        c = C[k - 1][_s - 1]
        s_without__s = S_final[:]
        s_without__s.remove(_s)
        g = G[_s][tuple(s_without__s)]
        if (c + g) < min_dist:
            min_dist = c + g
            min_dist_s = _s

    visited = []

    visited.append(k)
    P[k][tuple(S_final)] = min_dist_s

    returing_s = S_final[:]
    while (len(returing_s) > 0):
        k = min_dist_s
        returing_s.remove(k)
        visited.append(min_dist_s)
        min_dist_s = P[k][tuple(returing_s)]

    return min_dist, visited

print(tsp())
