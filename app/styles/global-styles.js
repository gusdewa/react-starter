import { createGlobalStyle } from 'styled-components';
import McKinseySansItalic from 'assets/fonts/McKinseySans-Italic.otf';
import McKinseySansMedium from 'assets/fonts/McKinseySans-Medium.otf';
import McKinseySansRegular from 'assets/fonts/McKinseySans-Regular.otf';
import CircularStdBlack from 'assets/fonts/CircularStd-Black.ttf';
import CircularStdBold from 'assets/fonts/CircularStd-Bold.ttf';
import CircularStdMedium from 'assets/fonts/CircularStd-Medium.ttf';

const GlobalStyle = createGlobalStyle`
  html,
  body {
    height: 100%;
    width: 100%;
    line-height: 1.5;
    font-size: 16px;
    overflow-x: hidden;
    
    @media (max-width: 420px) {
      font-size: 14px;
    }
  }

  #app {
    display: flex;
    flex-direction: column;
    background-color: ${({ theme }) => theme.colors.white};
    height: 100%; 
    min-width: 100%;
    overflow-x: hidden; 
  }

  p,
  label {
    line-height: 1.5;
    margin: 0;
    padding: 0;
  }

  h1, h2, h3, h4 {
    margin: 0;
  }

  @font-face {
    font-family: 'McKinseySans';
    font-style: italic;
    src: url(${McKinseySansItalic}) format('opentype');
  }

  @font-face {
    font-family: 'McKinseySans';
    font-style: oblique;
    src: url(${McKinseySansMedium}) format('opentype');
  }

  @font-face {
    font-family: 'McKinseySans';
    font-style: normal;
    src: url(${McKinseySansRegular}) format('opentype');
  }

  @font-face {
    font-family: 'CircularStd';
    font-weight: 900;
    src: url(${CircularStdBlack}) format('truetype');
  }

  @font-face {
    font-family: 'CircularStd';
    font-style: oblique;
    font-weight: 500;
    src: url(${CircularStdBold}) format('truetype');
  }
  
  @font-face {
    font-family: 'CircularStd';
    font-style: normal;
    src: url(${CircularStdMedium}) format('truetype');
  }

  /* Reset Button */
  button {
    border: none;
    margin: 0;
    width: auto;
    overflow: visible;
    outline: none;
    min-height: fit-content;

    cursor: pointer;
    min-width: 100%;

    line-height: normal;

    /* Corrects font smoothing for webkit */
    -webkit-font-smoothing: inherit;
    -moz-osx-font-smoothing: inherit;
  }

`;

export default GlobalStyle;
