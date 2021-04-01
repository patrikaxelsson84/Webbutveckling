import React, { Component, useState, useEffect } from "react";
import { getAllPokemon, getPokemon } from "./PokemonService";
import "./Style.css";

function Menu(props) {
  return (
    <>
      <div className="index-module__logo_container--sUGNH">
        <img
          alt="PokéAPI"
          src="https://raw.githubusercontent.com/PokeAPI/media/master/logo/pokeapi_256.png"
        />
      </div>
      <span className="Menu">Welcome to the Pokedex</span>
      <br></br>
      <button
        onClick={() => {
          props.changeView("pokemon");
        }}
      >
        Start your Pokedex here!
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
  const pokemon = props.data;
  //const [pokemon, setPokemon] = useState(null);

  /*useEffect(() => {
    getPokemon(props.data.url).then((json) => {
      setPokemon(json);
    });
  }, []);

  if (pokemon === null) return <div>nothing</div>;*/

  return (
    <>
      <div class="index-module__logo_container--sUGNH">
        <img
          alt="PokéAPI"
          src="https://raw.githubusercontent.com/PokeAPI/media/master/logo/pokeapi_256.png"
        />
      </div>

      <div>Name: {props.data.name}</div>
      <div>Abilities: {JSON.stringify(pokemon.abilities)}</div>
      <button
        onClick={() => {
          props.changeData(pokemon);
          props.changeView("pokemon");
        }}
      >
        Back
      </button>
    </>
  );
}

function Pokemon(props) {
  const [pokemonList, setPokemon] = useState([]);

  useEffect(() => {
    getAllPokemon().then((json) => {
      for (let result of json.results) {
        getPokemon(result.url).then((json) => {
          // Vi får in den gamla pokemonList, uppdaterar så att state är den gamla + det vi får in ifrån våran fetch/api:t
          setPokemon((pok) => pok.concat(json));
        });
      }
    });
  }, []);

  return (
    <div>
      <div className="index-module__logo_container--sUGNH">
        <img
          alt="PokéAPI"
          src="https://raw.githubusercontent.com/PokeAPI/media/master/logo/pokeapi_256.png"
        />
      </div>
      <div className="flex-container">
        Pokemon:
        <ul>
          {pokemonList.map((pokemon) => {
            return (
              <li key={pokemon.name}>
                <img src={pokemon.sprites.front_shiny.toString()} />
                <span>{pokemon.name}</span>
                {" * "}
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
