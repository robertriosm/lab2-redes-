
def viterbi(observed_sequence, states, start_prob, trans_prob, emit_prob):
    # Initialize the tables for V and path
    V = [{}]
    path = {}

    # Initialize start probabilities
    for y in states:
        V[0][y] = start_prob[y] * emit_prob[y][observed_sequence[0]]
        path[y] = [y]

    # Run Viterbi for t > 0
    for t in range(1, len(observed_sequence)):
        V.append({})
        newpath = {}

        for y in states:
            # Compute the probability for each state and choose the state with maximum probability
            (prob, state) = max([(V[t-1][y0] * trans_prob[y0][y] * emit_prob[y][observed_sequence[t]], y0) for y0 in states])
            V[t][y] = prob
            newpath[y] = path[state] + [y]

        # Don't need to remember the old paths
        path = newpath

    # Return the most probable path
    (prob, state) = max([(V[len(observed_sequence) - 1][y], y) for y in states])
    
    return (prob, path[state])


