const colors = require('tailwindcss/colors')

/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'selector',
  darkMode: ['variant', '&:not(.light *)'],
  content: ["./src/main/resources/**/*.{html,js,css}"],
  theme: {
    extend: {
      colors: {
        transparent: 'transparent',
        current: 'currentColor',
        black: colors.black,
        white: colors.white,
        emerald: colors.emerald,
        indigo: colors.indigo,
        yellow: colors.yellow,
        stone: colors.warmGray,
        sky: colors.lightBlue,
        neutral: colors.trueGray,
        gray: colors.coolGray,
        slate: colors.blueGray,
        primarydark: '#1b1b1a',
        secondarydark: '#282828',
        tertiarydark: '#1b1c1a'
      },
    },
  },
  plugins: [],
  
}
