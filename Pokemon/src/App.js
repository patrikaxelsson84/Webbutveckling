import React, { Component, useState, useEffect } from "react";
import { getAllPokemon, getPokemon } from "./PokemonService";
import "./Style.css";

function Menu(props) {
  return (
    <>
      <span className="Menu">MENU</span>
      <button
        onClick={() => {
          props.changeView("pokemon");
        }}
      >
        Pokemon
      </button>
    </>
  );
}

function ViewHandler() {
  const [view, setView] = useState("menu");
  const [viewData, setViewData] = useState(null);

  switch (view) {
    case "menu":
      return (
        <Menu changeView={setView} changeData={setViewData} data={viewData} />
      );
    case "pokemon":
      // Skicka med changeView för att kunna ändra view
      // Skicka med changeData för att kunna ändra datan som skickas med till nästa view
      // Skicka med data för att view skall kunna använda den
      return (
        <Pokemon
          changeView={setView}
          changeData={setViewData}
          data={viewData}
        />
      );
    case "pokemoninfo":
      return (
        <PokemonInfo
          changeView={setView}
          changeData={setViewData}
          data={viewData}
        />
      );
  }
}

function PokemonInfo(props) {
  const [pokemon, setPokemon] = useState(null);

  useEffect(() => {
    getPokemon(props.data.url).then((json) => {
      setPokemon(json);
    });
  }, []);

  if (pokemon === null) return <div>nothing</div>;

  return (
    <>
      <div>Name: {props.data.name}</div>
      <img src={JSON.stringify(pokemon.sprites.front_shiny)} />
      <div>Abilities: {JSON.stringify(pokemon.abilities)}</div>
    </>
  );
}

function Pokemon(props) {
  const [pokemon, setPokemon] = useState([]);

  useEffect(() => {
    getAllPokemon().then((json) => {
      console.log(json);
      setPokemon(json.results);
    });
  }, []);

  return (
    <div>
      <div class="index-module__logo_container--sUGNH">
        <img
          alt="PokéAPI"
          src="https://raw.githubusercontent.com/PokeAPI/media/master/logo/pokeapi_256.png"
        />
      </div>
      Pokemon:
      <ul>
        {pokemon.map((pokemon) => {
          return (
            <li key={pokemon.name}>
              <span>{pokemon.name}</span>

              <button
                onClick={() => {
                  props.changeData(pokemon);
                  props.changeView("pokemoninfo");
                }}
              >
                Info
              </button>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

function App() {
  return (
    <div className="App">
      <ViewHandler />
    </div>
  );
}

export default App;
