# Quick Start

1. Run: `npm i`
1. Run: `npm start`
2. Open the [localhost port 3000](http://localhost:3000) on your web browser.

# Code Scaffolding

You can use a `generate` command to create component or container with given template.

## Container

1. Run: `npm run generate container`
1. Put name of the new container
1. Put any color for the dummy background. This will help for layouting.
1. Go to the `/app/container` folder
1. Make some adjustment

## Component

1. Run: `npm run generate component`
1. Put name of the new component
1. Go to the `/app/component` folder
1. Make some adjustment

# State Management
We use React Context API and React useReducer helper to maintain a global state.

## Setting State
To set a global state, we do as follow:
```javascript
import { useCurrentData } from 'hooks';

function MakePayment() {
  const currentData = useCurrentData();
  const [amountToPay, setAmountToPay] = useState(500);

  const setGlobalDataFromState = () => {
      currentData.set({ amountToPay: amountToPay })
  };
// ...
``` 

## Getting State
To get a global state, we do as follow:
```javascript
import { useCurrentData } from 'hooks';

function MakePayment() {
  const currentData = useCurrentData();
  const [amountToPay, setAmountToPay] = useState();

  const setLocalStateFromGlobalState = () => {
      // Note that param must match the key on the setter
      const prevAmountToPay = currentData.get('amountToPay');
      setAmountToPay(prevAmountToPay);
  };
// ...
```
