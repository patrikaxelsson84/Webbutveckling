export async function getAllPokemon() {
  const response = await fetch("https://pokeapi.co/api/v2/pokemon");
  return response.json();
}

export async function getPokemon(url) {
  const response = await fetch(url);
  return response.json();
}
