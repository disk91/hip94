export const state = () => ({
    centerPosition : [45.8, 3.1],
    zoom : 4,
})

export const getters = {
  getCenterPosition(state) {
    return state.centerPosition;
  },
  getZoom(state) {
    return state.zoom;
  },
}

export const mutations = {

    setCenterPosition(state, b) {
        state.centerPosition = b;
    },
    setZoom(state, b) {
        state.zoom = b;
    },
}


export const actions = {

}