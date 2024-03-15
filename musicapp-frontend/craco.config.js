const path = require('path');

module.exports = {
  webpack: {
    alias: {
      Hooks: path.resolve(__dirname, 'src/hooks/'),
      Services: path.resolve(__dirname, 'src/services/'),
      Components: path.resolve(__dirname, 'src/components/'),
      Util: path.resolve(__dirname, 'src/util/')
    }
  }
};